## Creating a CI/CD pipeline using GitHub actions to execute Selenium test cases

### Description
The main aim of this project is to create a CI/CD pipeline to execute Selenium test cases using GitHub actions. Here the user enters a tag associated with test case they want to execute and runs a workflow.


### Code explanation

#### YML file to create a workflow

maven.yml
'''name: Cucumber Tests

on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]
  workflow_dispatch:
    inputs:
      tag:
        description: 'Tag for Cucumber tests'

jobs:
  build:

    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
        cache: maven

    - name: Download ChromeDriver
      run: |
        mkdir -p bin
        curl -L "https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/120.0.6099.109/linux64/chromedriver-linux64.zip" -o "bin/chromedriver.zip"
        unzip "bin/chromedriver.zip" -d "bin"
        chmod +x "bin/chromedriver-linux64/chromedriver"

    - name: Run Cucumber Tests
      run: |
        mvn test -Dwebdriver.chrome.driver=$GITHUB_WORKSPACE/bin/chromedriver -Dcucumber.filter.tags="${{ github.event.inputs.tag }}"'''
