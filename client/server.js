const express = require('express');

const pjson = require('./package.json');

const app = express();

app.use(express.static(`./dist/www`));

app.get('/*', (req, res) =>
    res.sendFile('index.html', {root: `dist/www`}),
);

console.log(`${pjson.name} running`)

app.listen(process.env.PORT || 8097);
