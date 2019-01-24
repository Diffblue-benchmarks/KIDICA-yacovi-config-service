  <h3>YaCoVi Config Service</h3>
  <p>
    Configuration Service for the <a href='https://github.com/KIDICA/yacovi-web' target='_blank'>YaCoVi</a> web application. This service is designed as a serverless function and is operated on the <a href='https://docs.microsoft.com/en-us/azure/azure-functions/' target='_blank'>Azure Function</a> platform.
    <br>
    <br>
    <a href="https://github.com/KIDICA/yacovi-config-service/issues/new">Report bug</a>
    <br>
    <a href="https://github.com/KIDICA/yacovi-config-service/issues/new">Request feature</a>
  </p>
</p>

## Table of contents

- [Getting started](#getting-started)
- [Copyright and license](#copyright-and-license)

## Getting started

### Prerequisites

**Warning**

> Verify that you are running at least node 8.9.x and npm 5.x.x by running node -v and npm -v in a terminal/console window. Older versions produce errors, but newer versions are fine.

> Please also make sure you have installed the [Azure CLI](https://docs.microsoft.com/en-us/cli/azure/install-azure-cli?view=azure-cli-latest) on your local machine!

Install Azure functions core tools using

```batchfile
npm i -g azure-functions-core-tools@core
```
### Compiling and Running

The following command must be executed to build the package:

```batchfile
./mvnw clean package
```

Then you may run it locally by:

```batchfile
./mvnw azure-functions:run
```

The timer and the queue functions will be triggered once per 30 seconds. To trigger the HTTP function, you need to execute the following command in a new command line window:

```batchfile
curl 'http://localhost:7071/api/GetYaCoViConfig' -H 'Accept: application/json' -H 'Authorization: Bearer <AZURE_TOKEN>' --compressed
```

To terminate the app, press `Ctrl + C`.

### Manual Deployment

Prior to manual deployment, you must log in using the Azure CLI with the following command:

```batchfile
az login --username "<KION E-MAIL>" --password "<PASSWORD>" --subscription "eec1832e-05fe-4bf8-80db-28f4f0de9d57"
```

> Please make sure you have access to the subscription 'R2S36_KION_Campus_POC'!

After the login, you can deploy the function app with the following command:

```batchfile
./mvnw azure-functions:deploy
```

And you will find your functions app named `yacovi-config-service` under `R2S36_KION_Campus_POC` subscription.

You will also be able to find the URL of the deployed app within the last several lines of the command line output. So it is possible to verify that it is actually running on Azure.

```batchfile
curl 'https://yacovi-config-service.azurewebsites.net/api/GetYaCoViConfig' -H 'Accept: application/json' -H 'Authorization: Bearer <TOKEN>' --compressed
```

## Travis CI

We use Travis CI to run this tasks in order:
* Tests
* Package
* Deploy on Microsoft Azure

## Copyright and license

Code and documentation copyright 2019 the authors. Code released under the [MIT License](https://github.com/KIDICA/yacovi-web/blob/master/LICENSE).

