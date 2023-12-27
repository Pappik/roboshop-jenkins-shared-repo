def call() {
    try {
        node('workstation') {

            stage('CleanUp') {
                cleanWs()
                git branch: 'main', url: "https://github.com/Pappik/${component}"
            }
            stage('compile') {
                common.compile()
            }

            stage('unit test') {
                commom.unittests()
            }

            stage('Quality Control') {
                sh "sonar-scanner -Dsonar.host.url=http://172.31.86.114:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=${component}"

            }
            stage('Upload Artifact') {
                echo 'Upload'
            }
        }
    }catch(Exception e) {
        common.email("failed")
    }
}

