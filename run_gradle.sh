#!/bin/bash


gradle_script="./gradlew"

usage(){
    echo "Usage(): $0 [RUN_OPTIONS]"
    echo "The following is the available [RUN_OPTIONS]: "
    echo "1. tasks"
    echo "2. build"
    echo "3. test"
    echo "4. functionalTest"
    echo "5. check"
    echo "6. bootRun"
    exit 1
}

setup_environment(){
    if [[ ! -f ${gradle_script} ]]
    then
        echo "Processing: ${gradle_script} is not found"
        exit 1
    fi
}

run_tasks(){
    case ${mode} in 
        1)  # List the available tasks
            bash ${gradle_script} tasks
            ;;
        2)  # Compiles Project > Run Tests > Create Executable Jar
            bash ${gradle_script} build
            ;;
        3)  # Run Unit Tests
            bash ${gradle_script} test
            ;;
        4)  # Run Functional Tests
            bash ${gradle_script} functionalTest
            ;;
        5)  # Run unit and functional tests
            bash ${gradle_script} check
            ;;
        6)  # Run application listening on port 8080
            bash ${gradle_script} bootRun
            ;;
        *) echo "Unknown Gradle Options"
            ;;
    esac
}

if [[ ! $# -eq 1 ]]
then
    usage
fi

mode="$1"
setup_environment
run_tasks