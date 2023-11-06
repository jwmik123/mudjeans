pipeline {
    agent any
    stages {
        stage('Test Docker') {
            steps {
                script {
                    sh 'docker --version' // This should print the Docker version if configured correctly
                }
            }
        }
    }
}
