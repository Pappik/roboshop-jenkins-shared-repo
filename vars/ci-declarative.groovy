def call() {
    try {
        pipeline {
            agent {
                label 'workstation'
            }

            stages {
                stage('compile/build') {
                    steps {
                        script {
                            common.compile()
                        }
                    }
                }

                stage('Unit Test') {
                    steps {
                        script {
                            common.unittests()
                        }
                    }
                }

                stage('Quality control') {
                    steps {
                        sh 'sonar-scanner -Dsonar.host.url=http://172.31.86.114:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=${component}'

                    }
                }

                stage('Upload Code') {
                    steps {
                        echo "code upload"
                    }
                }
            }
        }
    } catch(Exception e) {
      common.email("failed")
    }
}