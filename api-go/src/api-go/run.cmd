call "./test.cmd"
call "./build.cmd"
SET "GITHUB_CLIENT_ID=testclientid"
SET "GITHUB_SECRET=testclientsecret"
SET "GITHUB_TOKEN_URL=https://github.com/login/oauth/access_token?client_id=%%s&client_secret=%%s&code=%%s"
api-go.exe