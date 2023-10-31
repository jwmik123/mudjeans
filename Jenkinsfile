pipeline {
    agent any 

    stages {
        stage('Checkout') {
            steps {
                // Get the code from the version control system.
                checkout scm 
            }
        }

        stage('Build') {
            steps {
                // Commands to build the project
                echo 'Building...'
            }
        }

        stage('Test') {
            steps {
                // Commands to test the project
                echo 'Testing...'
            }
        }

        stage('Deploy') {
            steps {
                // Commands to deploy the project
                echo 'Deploying...'
            }
        }
    }

    post {
        success {
            // Actions to take if pipeline is successful, e.g., notifications
            echo 'Pipeline completed successfully.'
        }
        failure {
            // Actions to take if pipeline fails
            echo 'Pipeline failed.'
        }
    }
}
