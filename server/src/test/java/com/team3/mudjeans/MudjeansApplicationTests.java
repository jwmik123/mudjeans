package com.team3.mudjeans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.team3.mudjeans.entity.Jean;
import com.team3.mudjeans.entity.JeanOrder;
import com.team3.mudjeans.entity.Permission;
import com.team3.mudjeans.entity.User;
import com.team3.mudjeans.exceptions.ResourceNotFoundException;
import com.team3.mudjeans.repository.EntityRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MudjeansApplicationTests {
    @Autowired
    private EntityRepository<Jean> jeans;

    @Autowired
    private EntityRepository<JeanOrder> order;

    @Autowired
    private EntityRepository<Permission> permission;

    @Autowired
    private EntityRepository<User> user;

    @Autowired
    private MockMvc mvc;

    @LocalServerPort
    private int port;

    @BeforeEach
    void loadData() {
        Jean j = new Jean(17,"productCode1", "description", "L", 12, 456, 123);
        jeans.save(j);

        Permission p = new Permission(1, "default");
        permission.save(p);
        p = new Permission(2, "admin");
        permission.save(p);

        User u = new User(1,"test@email","test",p);
        user.save(u);
    }

    //USER TESTS:
    // Michael Test 1
    @Test
    void testGetAllUsersREST() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                // endpoint for request
                .get("/users")
                // declarate that it should be JSON and print
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                // check if the satus is 200
                .andExpect(status().isOk())
                // check if json is returned and if the id in the first place of the array is a number and a 1
                .andExpect(content().contentType("application/json")).andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    // Michael Test 2
    @Test
    void testRESTGetUserById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                // get request for the endpoint:
                .get("/users/1")
                // declarate that it should be JSON and print
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                // check if the satus is 200
                .andExpect(status().isOk())
                // check if json is returned and if the id in the first place of the array is a number and a 1
                .andExpect(content().contentType("application/json")).andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.id").value(1));
    }

    // Michael Test 3
    @Test
    @Rollback(false)
    void testSaveUserRepo() {
        // initialize an email to compare it later on
        String email = "email@test.com";
        //making an user
        Permission permissionSaved = new Permission(2, "admin");
        User userSaved = user.save(new User(10,email, "email", permissionSaved));

        // checks if the id is 1 or bigger
        Assertions.assertFalse(userSaved.getId() <= 0);

        // checks if the email from the saved email is the same with the entered email.
        Assertions.assertEquals(userSaved.getEmail(), email);
    }

    // Michael Test 4
    @Test
    @Rollback(false)
    void testDeleteUserRepo() throws Exception {
        //making an user
        Permission permissionSaved = new Permission(2, "admin");
        User userSaved = user.save(new User(1,"email@test.com", "email", permissionSaved));
        // throws an exception when the id is not the id from the saved user.
        if(userSaved.getId() != 1) {
            //catch mistake
            throw new Exception("User with this id cannot be deleted");
        } else {
            // if it is the right id, the user is deleted
            user.deleteById(1);
        }
        // checks if user cant be found anymore
        Assertions.assertNull(user.findById(1));

    }

    // Michael Test 5
    @Test
    void testPostUserREST() throws Exception {
        // create user
        Permission permission = new Permission();
        permission.setId(2);
        permission.setPermissionName("admin");
        User user = new User();
        user.setId(1);
        user.setEmail("email@test.com");
        user.setPassword("email");
        user.setPermission(permission);

        // mapper to save the user in a JSON format
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String JSONOfUser = objectWriter.writeValueAsString(user);

        mvc.perform(MockMvcRequestBuilders
                // post request for the endpoint:
                .post("/users")
                // declare that it should be JSON, and put user in. then print it.
                .contentType(MediaType.APPLICATION_JSON).content(JSONOfUser)).andDo(print())
                // checks if status 201 is returend.
                .andExpect(status().isCreated())
                // check if json is returned and if the id in the first place of the array is a number and a 1
                .andExpect(content().contentType("application/json")).andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(1));


    }

    //ORDERS TESTS:
    //Tests if getting ALL orders works
    @Test
    void testGetAllOrders() throws Exception {
        //create a mock request
        mvc.perform(MockMvcRequestBuilders
                //url where to request from
                .get("/orders")
                //return type of the request
                .accept(MediaType.APPLICATION_JSON))
                //print the result of the json
                .andDo(print())
                //check if the status code of the request is ok
                .andExpect(status().isOk())
                //check if the return type of the content is application/json
                .andExpect(content().contentType("application/json"))
                //check if there is json given back
                .andExpect(jsonPath("$").exists())
                //check if the first item in the array is an orderNumber which is a number
                .andExpect(jsonPath("$[0].orderNumber").isNumber())
                //check if the first item in the array has the orderNumber 1
                .andExpect(jsonPath("$[0].orderNumber").value(1));
    }

    //Tests if getting an order by a specific ID works
    @Test
    void testGetOrderById() throws Exception {
        mvc.perform(MockMvcRequestBuilders
            .get("/orders/3")
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.orderNumber").isNumber())
            .andExpect(jsonPath("$.orderNumber").value(3));
    }

    //JEANS TESTS
    @Test
    void testGetAllJeans() throws Exception {
        mvc.perform(MockMvcRequestBuilders
        .get("/jean")
        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.description[*].productCode").value("productCode1"));
    }


    //Tests if the save method of the Jeans work
    @Test
    @Rollback(false)
    void testSaveJeanOrder() {
        Jean savedJean = jeans.save(new Jean(456, "testCode"));

        //Tests if the ID is lower than 0
        Assertions.assertFalse(savedJean.getId() < 0);

        //Tests if productCode equals the String that is given
        Assertions.assertEquals(savedJean.getProductCode(), "testCode");
    }

    //Tests if the save method of the Jeans work
    @Test
    @Rollback(false)
    void testDeleteJeanById() throws Exception {
        //Create Jean that has to be deleted
        Jean savedJean = jeans.save(new Jean(21,"deleteThisJean"));

        if(savedJean.getId() != 21) {
            //catch mistake
            System.out.println("Something went wrong!");
        } else {
            //Delete Jean
            jeans.deleteById(21);
        }

    }

    //Semih
    //Tests if the order method of the Jeans work
    @Test
    @Rollback(false)
    void testJeanOrderId() throws Exception {
        //Create and save a fake JeanOrder
        JeanOrder jeanOrder = order.save(new JeanOrder(10, "testOrder", 10,
                "testProductCode", "testDescription", "testFabric",
                "medium", 0));


        // Test if orderNumber is equal to the given
        Assertions.assertFalse(jeanOrder.getOrderNumber() != jeanOrder.getId());

        Assertions.assertEquals(jeanOrder.getOrderNumber(), jeanOrder.getId());


    }

    //Semih
    @Test
    @Rollback(false)
    void testJeanOrderQuantity() throws Exception {

        //Create and save a fake JeanOrder

        JeanOrder jeanOrder = order.save(new JeanOrder(11, "testOrder", 100,
                "testProductCode", "testDescription", "testFabric",
                "large", 6));


        // Test if quantity is larger then 0

        Assertions.assertTrue(jeanOrder.getQuantity() > 0);


    }

    //Semih
    @Test
    @Rollback(false)
    void testJeanOrderDescription() throws Exception {

        //Create and save a fake JeanOrder
        JeanOrder jeanOrder = order.save(new JeanOrder(12, "testOrder", 122,
                "testProductCode", "testDescription", "testFabric",
                "small", 24));


//        // Test if description of JeanOrder is equal to the given fake description
        Assertions.assertEquals(jeanOrder.getDescription(), "testDescription");


    }

    //Semih
    @Test
    @Rollback(false)
    void testJeanOrderFabric() throws Exception {
        //Create and save a fake JeanOrder
        JeanOrder jeanOrder = order.save(new JeanOrder(12, "testOrder", 122,
                "testProductCode", "testDescription", "testFabric",
                "large", 12));

//        // Test if fabric of JeanOrder is equal to the given fake description
        Assertions.assertEquals(jeanOrder.getFabric(), "testFabric");


    }


    //Semih
    @Test
    void testGetPermissionById() throws Exception {
        //Test by checking if the permission works with the given id (2)
        mvc.perform(MockMvcRequestBuilders
                //get request from url
                .get("/permissions/2")
                //type of request
                .accept(MediaType.APPLICATION_JSON))
                //after that print the json result
                .andDo(print())
                // a check on the status code
                .andExpect(status().isOk())
                // a check if the contenttype is application/json
                .andExpect(content().contentType("application/json"))
                // a check if the json is given
                .andExpect(jsonPath("$").exists())
                // a check that the id is a number
                .andExpect(jsonPath("$.id").isNumber())
                // a check that the specific id has the value 2
                .andExpect(jsonPath("$.id").value(2));
    }


    @Test
    void testDeleteOrder() throws Exception {
        mvc.perform(MockMvcRequestBuilders
            .delete("/orders/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$").isBoolean())
        .andExpect(jsonPath("$").value(true));
    }

    @Test
    void testFileActions() throws Exception {
        File file = new File("src/main/resources/static/uploads/ExampleExcel.xlsx");
        file.deleteOnExit();
        if (file.createNewFile()) {
            Workbook w = new XSSFWorkbook();
            w.createSheet();
            Sheet s = w.getSheetAt(0);
            s.createRow(0);
            Row r = s.getRow(0);

            s.createRow(1);
            r = s.getRow(1);
            for (int i = 0; i < 20; i++) r.createCell(i);
            r.getCell(0).setCellValue("MB0001C001D001-28-32");
            r.getCell(1).setCellValue("Regular Bryce - Strong Blue ");
            r.getCell(2).setCellValue("W28 L32");
            r.getCell(16).setCellValue("7");
            r.getCell(17).setCellValue("6");
            r.getCell(15).setCellValue("3");
            r.getCell(14).setCellValue("3");
            r.getCell(13).setCellValue("1");

            s.createRow(2);
            r = s.getRow(2);
            for (int i = 0; i < 20; i++) r.createCell(i);
            r.getCell(0).setCellValue("MB0001C001D001-30-32");
            r.getCell(1).setCellValue("Regular Bryce - Strong Blue ");
            r.getCell(2).setCellValue("W30 L32");
            r.getCell(16).setCellValue("8");
            r.getCell(17).setCellValue("5");
            r.getCell(15).setCellValue("4");
            r.getCell(14).setCellValue("2");
            r.getCell(13).setCellValue("");
            FileOutputStream fileOut = new FileOutputStream(file);
            w.write(fileOut);
            fileOut.close();

            byte[] content = null;
            content = Files.readAllBytes(file.toPath());

            MockMultipartFile mFile = new MockMultipartFile("file", "ExampleExcel.xlsx", "application/vnd.ms-excel", content);

            mvc.perform(MockMvcRequestBuilders.multipart("/orders/upload")
                    .file(mFile)
                    ).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$['Regular Bryce - Strong Blue '][0].productCode").isString())
                    .andExpect(jsonPath("$['Regular Bryce - Strong Blue '][0].productCode").value("MB0001C001D001-28-32"))
                    .andExpect(jsonPath("$['Regular Bryce - Strong Blue '][0].lastThreeMonths").isNumber())
                    .andExpect(jsonPath("$['Regular Bryce - Strong Blue '][0].lastThreeMonths").value(7))
                    .andExpect(jsonPath("$['Regular Bryce - Strong Blue '][1].productCode").isString())
                    .andExpect(jsonPath("$['Regular Bryce - Strong Blue '][1].productCode").value("MB0001C001D001-30-32"))
                    .andExpect(jsonPath("$['Regular Bryce - Strong Blue '][1].lastThreeMonths").isNumber())
                    .andExpect(jsonPath("$['Regular Bryce - Strong Blue '][1].lastThreeMonths").value(6));

            MvcResult result = mvc.perform(MockMvcRequestBuilders
                    .get("/downloadFile/ExampleExcel.xlsx")
                    .accept(MediaType.APPLICATION_OCTET_STREAM)
                ).andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();
            Assertions.assertArrayEquals(result.getResponse().getContentAsByteArray(), Files.readAllBytes(file.toPath()));
        }
    }

    @Test
    void testGetAllPermissions() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/permissions")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].permissionName").isString())
                .andExpect(jsonPath("$[0].permissionName").value("default"));
    }

    @Test
    void testSavePermissionToDB() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/permissions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":2,\"permissionName\":\"testPermission\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.permissionName").isString())
                .andExpect(jsonPath("$.permissionName").value("testPermission"));
    }
}
