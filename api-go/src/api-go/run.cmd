call "./test.cmd"
call "./build.cmd"
SET "GITHUB_TOKEN_URL=https://github.com/login/oauth/access_token?client_id=[PUT_CLIENT_ID_HERE]&client_secret=[PUT_CLIENT_SECRET_HERE]&code=%%s"
api-go.exe