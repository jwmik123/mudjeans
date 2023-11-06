pipeline {
    agent any
    
    stages {

        stage('Test Docker') {
            steps {
                sh 'docker info'
                // Or a simple test command like
                // sh 'docker run hello-world'
            }

    }
    
    post {
        always {
            // Clean up test server container
            sh 'docker rm -f test-server || true'
        }
    }
}
}
