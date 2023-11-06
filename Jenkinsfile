pipeline {
    agent {
        docker {
            label 'my-docker-label'
            image 'my-docker-image'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
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
