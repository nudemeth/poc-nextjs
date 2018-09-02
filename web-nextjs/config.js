const port = process.env.NODE_ENV !== 'production' ? ":5000" : "";
const config = {
    api: {
        catalog: {
            uri: `http://catalog-csharp${port}/api/v1/catalog/`
        }
    }
}

export default config;