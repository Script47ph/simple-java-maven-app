node {
  checkout scm
  docker.image('maven:3.9.0').inside('-v /root/.m2:/root/.m2') {
    stage('Build') {
      sh 'mvn -B -DskipTests clean package'
    }
  }
}
