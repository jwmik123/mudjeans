pipeline {
    agent any
    
    stages {
        stage('Test Docker') {
            steps {
                sh 'docker run hello-world'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image
                    docker.build("my-tomcat-app:${env.BUILD_ID}")
                }
            }
        }
        
        stage('Start Test Server') {
            steps {
                script {
                    // Run your test server container
                    docker.run(
                        "--name test-server -d -p 8080:8080 my-tomcat-app:${env.BUILD_ID}"
                    )
                }
            }
        }
        
        stage('Test Application') {
            steps {
                // Steps to test your application
                // You could use any testing framework that makes HTTP requests to your test server
            }
        }
        
        stage('Deploy to Deploy Server') {
            steps {
                script {
                    // Stop the old deploy server container
                    sh 'docker rm -f deploy-server || true'
                    // Run your deploy server container
                    docker.run(
                        "--name deploy-server -d -p 80:8080 my-tomcat-app:${env.BUILD_ID}"
                    )
                }
            }
        }
    }
    
    post {
        always {
            // Clean up test server container
            sh 'docker rm -f test-server || true'
        }
    }
}
