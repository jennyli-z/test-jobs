set -e -o pipefail 
JENKINS_URL=https://ci.milvus.io:18080/jenkins
# JENKINS_CRUMB is needed if your Jenkins controller has CRSF protection enabled as it should
JENKINS_CRUMB=`curl "$JENKINS_URL/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,\":\",//crumb)"`

while (( "$#" )); do
  case "$1" in

    --jenkins-file)
      JENKINS_FILE=$2
      shift 2
    ;;
  
    -h|--help)
      { set +x; } 2>/dev/null
      HELP="
Usage:
  $0 [flags] [Arguments]

    --jenkins-file             Jenkins file absolute path

    -h or --help               Print help information


Use \"$0  --help\" for more information about a given command.
"
      echo -e "${HELP}" ; exit 0
    ;;
    -*)
      echo "Error: Unsupported flag $1" >&2
      exit 1
      ;;
    *) # preserve positional arguments
      PARAMS+=("$1")
      shift
      ;;
  esac
done


function validate(){
    local file_path=${1:-Jenkinsfile}
    response=$(curl -X POST -H $JENKINS_CRUMB -F "jenkinsfile=<${file_path}" $JENKINS_URL/pipeline-model-converter/validate)
    
    if [[ ${response} =~ "Error"  ]]
    then
        echo " ${response}"
        echo "Validate ${file_path} failed !"
        
        exit 1  
    fi
}
validate ${JENKINS_FILE} 


