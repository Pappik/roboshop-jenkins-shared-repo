def call() {
    pipeline {
        agent {
            label 'workstation'
        }

        stages {
            stage('compile/build') {
                steps {
                    echo "compile"
                }
            }

            stage('Unit Test') {
                steps {
                    echo "Unit Test"
                }
            }

            stage('Quality control') {
                steps {
                    echo "Quality control"
                }
            }

            stage('Upload Code') {
                steps {
                    echo "code upload"
                }
            }
        }
    }
}