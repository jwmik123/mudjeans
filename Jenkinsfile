pipeline {
    agent any 

    stage('Build & Test') {
    steps {
        // Your build and test commands
    }
}

    stage('Static Analysis') {
    steps {
        // Integrate with tools like SonarQube or similar
    }
}

    stage('Notify') {
    steps {
        email (
            subject: "Code Analysis Result",
            body: "Your static code analysis is completed. Check the report.",
            recipientProviders: [[$class: 'Developers']]
        )
    }
}

    stage('Deploy to Development') {
    steps {
        // Build Docker image and run for development environment
    }
}

stage('Deploy to Test') {
    steps {
        // Run for the test environment
    }
}

stage('Deploy to Acceptance/Production') {
    when {
        // Define when you want this to run, e.g., after manual approval
    }
    steps {
        // Run for the production environment
    }
}

}
