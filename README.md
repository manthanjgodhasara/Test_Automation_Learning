## Creating a CI/CD pipeline using GitHub actions to execute Selenium test cases

### Description
The main aim of this project is to create a CI/CD pipeline to execute Selenium test cases using GitHub actions. Here the user enters a tag associated with test case they want to execute and runs a workflow.


### Code explanation

#### YML file to create a workflow

<pre>
name: Cucumber Tests

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
        mvn test -Dwebdriver.chrome.driver=$GITHUB_WORKSPACE/bin/chromedriver -Dcucumber.filter.tags="${{ github.event.inputs.tag }}"
</pre>

##### YML file explanation

<pre>
name: Cucumber Tests
</pre>

This line sets the name of the GitHub Actions workflow. In this case, it's named "Cucumber Tests."

<pre>
on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]
  workflow_dispatch:
    inputs:
      tag:
        description: 'Tag for Cucumber tests'
</pre>

The on key specifies the events that trigger this workflow. In this case, the workflow is triggered on pushes to the master branch, pull requests targeting the master branch, and manually through a workflow dispatch event. The workflow dispatch event allows you to trigger the workflow manually and provide an input named tag with a description.

<pre>
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
</pre>

Defines a job named "build" and specifies that this job will run on the latest version of the Ubuntu operating system. Uses the actions/checkout action to check out the repository at the specified version (v3 in this case).

<pre>
- name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
        cache: maven
</pre>

Sets up JDK 11 using the actions/setup-java action. It specifies the Java version, distribution, and enables caching for Maven dependencies.

<pre>
- name: Download ChromeDriver
      run: |
        mkdir -p bin
        curl -L "https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/120.0.6099.109/linux64/chromedriver-linux64.zip" -o "bin/chromedriver.zip"
        unzip "bin/chromedriver.zip" -d "bin"
        chmod +x "bin/chromedriver-linux64/chromedriver"
</pre>

Downloads ChromeDriver for Selenium tests. It creates a bin directory, downloads the ChromeDriver ZIP file, extracts it, and sets execute permissions.

<pre>
- name: Run Cucumber Tests
      run: |
        mvn test -Dwebdriver.chrome.driver=$GITHUB_WORKSPACE/bin/chromedriver -Dcucumber.filter.tags="${{ github.event.inputs.tag }}"
</pre>

Runs Cucumber tests using Maven. It sets the path to the ChromeDriver and includes a Cucumber tag filter based on the input provided during the workflow dispatch.

#### Code for webdriver

<pre>
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless");
WebDriverManager.chromedriver().setup();
driver = new ChromeDriver(options);
</pre>

ChromeOptions options = new ChromeOptions();: Creates an instance of ChromeOptions, which is used to configure various Chrome browser settings.

options.addArguments("--headless");: Adds the argument --headless to the Chrome options. This makes the browser run in headless mode, meaning it operates without a graphical user interface.

WebDriverManager.chromedriver().setup();: Uses the WebDriverManager library to automatically download and set up the ChromeDriver executable. This helps in managing the WebDriver binaries without manual downloads.

driver = new ChromeDriver(options);: Initializes a new instance of the ChromeDriver, passing the configured ChromeOptions to launch the Chrome browser in headless mode.

#### Code for pom.xml

<pre>
  
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version> <!-- Use the latest version -->
            <configuration>
                <systemPropertyVariables>
                    <webdriver.chrome.driver>${webdriver.chrome.driver}</webdriver.chrome.driver>
                </systemPropertyVariables>
            </configuration>
        </plugin>
    </plugins>
</build>
  
</pre>

<systemPropertyVariables>: It is used to set system properties for the test execution. In this case, it sets the webdriver.chrome.driver system property to the value of the Maven property ${webdriver.chrome.driver}.
${webdriver.chrome.driver} is a placeholder for the path to the ChromeDriver executable.
