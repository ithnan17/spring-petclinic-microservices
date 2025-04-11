pipeline {
    agent {
        label 'maven-agent'
    }

    environment {
        GIT_URL = 'https://github.com/ithnan17/spring-petclinic-microservices.git'
        SKIP_PIPELINE = 'false'
    }

    stages {
        stage('Detect Changed Service') {
            steps {
                script {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/${env.BRANCH_NAME}"]],
                        userRemoteConfigs: [[
                            url: env.GIT_URL,
                            credentialsId: 'github-token',
                            refspec: '+refs/heads/*:refs/remotes/origin/*'
                        ]]
                    ])

                    sh "git fetch origin main"
                    def changedFiles = sh(
                        script: "git diff --name-only origin/main...HEAD",
                        returnStdout: true
                    ).trim().split('\n')

                    echo "Changed Files: ${changedFiles}"

                    def changedServices = changedFiles.findAll { it.startsWith("spring-petclinic-") }
                        .collect { it.split("/")[0] }
                        .unique()

                    if (changedServices.size() == 0) {
                        echo "No microservice directories changed. Skipping test and build stages."
                        env.SKIP_PIPELINE = 'true'
                        return
                    }

                    env.CHANGED_SERVICES = changedServices.join(',')
                    echo "Changed Services: ${env.CHANGED_SERVICES}"
                }
            }
        }

        stage('Test') {
            when {
                expression { return env.SKIP_PIPELINE != 'true' && env.CHANGED_SERVICES }
            }
            steps {
                script {
                    def services = env.CHANGED_SERVICES?.split(',') ?: []
                    for (service in services) {
                        dir(service) {
                            echo "Running tests for ${service}"
                            sh "mvn verify -P Jacoco -Deureka.client.enabled=false"
                        }
                    }
                }
            }
        }

        stage('Build') {
            when {
                expression { return env.SKIP_PIPELINE != 'true' && env.CHANGED_SERVICES }
            }
            steps {
                script {
                    def services = env.CHANGED_SERVICES?.split(',') ?: []
                    for (service in services) {
                        dir(service) {
                            echo "Building ${service}"
                            sh "mvn package -DskipTests"
                        }
                    }
                }
            }
        }

        stage('Check Coverage') {
            when {
                expression { return env.SKIP_PIPELINE != 'true' && env.CHANGED_SERVICES }
            }
            steps {
                script {
                    def services = env.CHANGED_SERVICES?.split(',') ?: []
                    for (service in services) {
                        dir(service) {
                            echo "Checking code coverage for ${service}"
                            def reportFile = "target/site/jacoco/jacoco.xml"
                            if (!fileExists(reportFile)) {
                                error "Coverage report not found for ${service}"
                            }

                            def coverage = sh(
                                script: "grep '<counter type=\"INSTRUCTION\"' ${reportFile} | sed -E 's/.*covered=\"([0-9]+)\".*missed=\"([0-9]+)\".*/\\1 \\2/'",
                                returnStdout: true
                            ).trim().split(" ")

                            def covered = coverage[0] as Integer
                            def missed = coverage[1] as Integer
                            def percent = (covered * 100) / (covered + missed)

                            echo "${service} coverage: ${percent}% (covered: ${covered}, missed: ${missed})"
                            if (percent < 70) {
                                error "${service} has insufficient coverage (${percent}%)"
                            }
                        }
                    }
                }
            }
        }

        stage('Publish Test & Coverage Report') {
            when {
                expression { return env.SKIP_PIPELINE != 'true' && env.CHANGED_SERVICES }
            }
            steps {
                script {
                    def services = env.CHANGED_SERVICES?.split(',') ?: []
                    for (service in services) {
                        dir(service) {
                            junit 'target/surefire-reports/*.xml'
                            publishHTML(target: [
                                reportDir: 'target/site/jacoco',
                                reportFiles: 'index.html',
                                reportName: "${service} JaCoCo Report",
                                keepAll: true,
                                alwaysLinkToLastBuild: true,
                                allowMissing: false
                            ])
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'CI Process Completed'
        }
        failure {
            echo 'CI Failed'
        }
        success {
            echo 'CI Passed'
        }
    }
}
