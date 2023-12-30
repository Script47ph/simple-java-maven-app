node {
  checkout scm
  docker.image('maven:3.9.0').inside('-v /root/.m2:/root/.m2 -p 8000:8000') {
    stage('Build') {
      sh 'mvn -B -DskipTests clean package'
    }
    try {
      stage('Test') {
        sh 'mvn test'
      }
    }
    catch (e) {
      echo "Test stage failed, cannot run next stage"
    }
    finally {
      junit 'target/surefire-reports/*.xml'
    }
    stage('Manual Approval') {
      input message: 'Lanjutkan ke tahap Deploy? (Klik "Proceed" untuk mengakhiri)'
    }
    stage('Deploy') {
      sh './jenkins/scripts/deliver.sh'
      sh 'sleep 60'
      sh './jenkins/scripts/kill.sh'
    }
  }
}
