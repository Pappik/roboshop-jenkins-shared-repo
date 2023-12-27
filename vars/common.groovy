def compile() {
    if (app_lang == "nodejs" ) {
        sh 'npm install'
    }

    if (app_lang == "maven" ) {
        sh 'mvn package'
    }
}

def unittests() {
    if (app_lang == "nodejs" ) {
        sh 'npm test || true'
    }

    if (app_lang == "maven" ) {
        sh 'mvn test'
    }

    if (app_lang == "python" ) {
        sh 'python3 -m unittest'
    }
}

def email(email_note) {
    mail bcc: '', body: "Job failed - ${JOB_BASE_NAME}\nJenkins URL - ${JOB_URL} ", cc: '', from: 'paparao.prema75@gmail.com', replyTo: '', subject: 'test from cart', to: 'paparao.prema75@gmail.com'
}

def artifact_Push() {
    sh "echo ${TAG_NAME} >VERSION"
    if (app_lang == "nodejs") {
       sh "zip -r  ${component}-${TAG_NAME}.zip nodule_modules server.js VERSION ${extrafiles}"
    }
    curl -v -u admin:admin123 --upload-file ${component}-${TAG_NAME}.zip http://172.31.88.150:8081/repository/${component}/${component}-${TAG_NAME}.zip
}