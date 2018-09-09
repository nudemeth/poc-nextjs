const isDev = process.env.NODE_ENV !== 'production';
const port = isDev ? ":5000" : "";
const dns = isDev ? "localhost" : "catalog-csharp.localhost";
const protocal = typeof window === 'undefined' ? "http" : "https";
const config = {
    api: {
        catalog: {
            uri: `${protocal}://${dns}${port}/api/v1/catalog/`
        }
    }
}

export default config;