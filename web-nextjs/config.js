const protocal = process.env["NODE_ENV"] === "production" ? "https" : "http"
const config = {
    api: {
        catalog: {
            uri: `${protocal}://localhost:5000/api/v1/catalog/`
        }
    }
}

export default config;