const isDev = process.env.NODE_ENV !== 'production';
const port = isDev ? 5000 : 80;
const dns = isDev ? "localhost" : "catalog-csharp";
const config = {
    api: {
        catalog: {
            uri: `http://${dns}:${port}/api/v1/catalog/`
        }
    }
}

export default config;