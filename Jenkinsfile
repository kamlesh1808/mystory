// Jenkinsfile - Jenkins pipeline with Gradle and Maven build, and Sonar Qube steps

pipeline {
    agent any

    triggers {
        pollSCM('*/2 * * * *')
    }

    tools {
        maven 'maven-3.6.3'
    }

    stages {

        stage ("Print Environments Info") {
            steps {
                printOS()
                printGradleVersion()
                printMavenVersion()
            }
        }

        stage('Gradle Build') {
            steps {
                gradlew('clean', 'classes')
            }
        }

        stage('Maven Build') {
            steps {
                mavenTasks('clean', 'package')
            }
        }

        stage('Unit Tests') {
            steps {
                gradlew('test')
            }

            post {
                always {
                    junit '**/build/test-results/test/TEST-*.xml'
                }
            }
        }

        //         stage('Sonar Qube') {
        //             steps {
        //                gradlew('sonarqube')
        //             }
        //         }

    }

    post {
        failure {
            echo "build failed!!!"
        }

        success {
            echo "build success!!!"
        }
    }

}

def printOS() {
    println "OS Name: " + System.properties['os.name']
}

def isOSWindows() {
    return (System.properties['os.name'].toLowerCase().contains('windows'))
}

def printGradleVersion(String... args) {
    if (isOSWindows()) {
        bat "./gradlew -v"
    } else {
        sh "./gradlew -v"
    }
}

def gradlew(String... args) {
    if (isOSWindows()) {
        bat "./gradlew ${args.join(' ')} -s"
    } else {
        sh "./gradlew ${args.join(' ')} -s"
    }
}

def printMavenVersion(String... args) {
    if (isOSWindows()) {
        bat "mvn -v"
    } else {
        sh "mvn -v"
    }
}

def mavenTasks(String... args) {
    if (isOSWindows()) {
        bat "mvn ${args.join(' ')}"
    } else {
        sh "mvn ${args.join(' ')}"
    }
}