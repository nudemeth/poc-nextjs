import React from 'react'
import Document, { Head, Main, NextScript } from 'next/document'
import { JssProvider }  from 'react-jss'
import getCssContext from '../components/common/getCssContext'

class DefaultDocument extends Document {
    static getInitialProps({ renderPage }) {
        // Resolution order
        //
        // On the server:
        // 1. page.getInitialProps
        // 2. document.getInitialProps
        // 3. page.render
        // 4. document.render
        //
        // On the server with error:
        // 2. document.getInitialProps
        // 3. page.render
        // 4. document.render
        //
        // On the client
        // 1. page.getInitialProps
        // 3. page.render

        // Get the context of the page to collected side effects.
        const cssContext = getCssContext()
        const page = (Page) => (props) => {
            const jss = <JssProvider
                registry={cssContext.sheetsRegistry}
                generateClassName={cssContext.generateClassName}
            >
                <Page cssContext={cssContext} {...props} />
            </JssProvider>
            return jss
        }
        const renderedPage = renderPage(page)

        return {
            ...renderedPage,
            cssContext,
            styles: (
                <style id='jss-server-side' dangerouslySetInnerHTML={{ __html: cssContext.sheetsRegistry.toString() }} />
            )
        }
    }

    render() {
        return (
            <html>
                <Head>
                    <meta charSet='utf-8' />
                    <meta name='author' content='nudemeth' />
                    {/* Use minimum-scale=1 to enable GPU rasterization */}
                    <meta
                        name='viewport'
                        content={
                            'user-scalable=0, initial-scale=1, ' +
                            'minimum-scale=1, width=device-width, height=device-height'
                        }
                    />
                    {/* PWA primary color */}
                    <meta name='theme-color' content={this.props.cssContext.theme.palette.primary[500]} />
                    <link href='https://fonts.googleapis.com/css?family=Roboto:300,400,500' rel='stylesheet' type='text/css' />
                    <link href='https://fonts.googleapis.com/icon?family=Material+Icons' rel='stylesheet' type='text/css' />
                    <link href='./static/css/style.css' rel='stylesheet' type='text/css' />
                </Head>
                <body>
                    <Main />
                    <NextScript />
                </body>
            </html>
        )
    }
}

export default DefaultDocument