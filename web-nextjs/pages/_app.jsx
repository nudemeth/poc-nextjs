import React from 'react'
import App, { Container } from 'next/app'
import Router from 'next/router'
import { MuiThemeProvider } from '@material-ui/core/styles'
import CssBaseline from '@material-ui/core/CssBaseline'
import JssProvider from 'react-jss/lib/JssProvider'
import { Provider } from 'react-redux'
import withRedux from 'next-redux-wrapper'
import withReduxSaga from 'next-redux-saga'
import getPageContext from '../components/common/getPageContext'
import configureStore from '../store/store'
import { storeAccessToken, storeAuthSites } from '../actions/identity.actions'
import config from '../config'

class MyApp extends App {
    constructor(props) {
        super(props)
        this.pageContext = getPageContext()
    }

    static async getInitialProps ({ Component, ctx }) {
        let pageProps = {}

        if (ctx.isServer) {
            ctx.store.dispatch(storeAccessToken(ctx.query.accessToken))
            ctx.store.dispatch(storeAuthSites(ctx.query.sites))
        }

        if (Component.getInitialProps) {
            pageProps = await Component.getInitialProps({ ctx })
        }

        if (!ctx.isServer) {
            return { pageProps }
        }

        // Wait Saga task to be done on server side
        await ctx.store.waitSagaTaskDone(ctx.isServer)

        const accessToken = ctx.store.getState().identityReducer.accessToken

        if (this.isRedirectToLogin(accessToken, ctx.pathname)) {
            this.redirect(ctx.res, '/login')
        }
        if (this.isRedirectToHome(accessToken, ctx.pathname)) {
            this.redirect(ctx.res, '/')
        }

        return { pageProps }
    }

    static redirect(res, path) {
        if (!res) {
            Router.push(path)
            return
        }

        res.writeHead(302, {
            Location: path
        })
        res.end()
    }

    static isRedirectToLogin(accessToken, pathname) {
        return !accessToken && !config.noAuthPaths.includes(pathname)
    }

    static isRedirectToHome(accessToken, pathname) {
        return accessToken && pathname === '/login'
    }

    componentDidMount() {
        // Remove the server-side injected CSS.
        const jssStyles = document.querySelector('#jss-server-side')
        const accessToken = this.props.store.getState().identityReducer.accessToken
        const pathname = this.props.router.pathname
        if (jssStyles && jssStyles.parentNode) {
            jssStyles.parentNode.removeChild(jssStyles)
        }
        if (MyApp.isRedirectToHome(accessToken, pathname)) {
            this.props.router.push('/')
        }
    }

    componentDidUpdate() {
        const accessToken = this.props.store.getState().identityReducer.accessToken
        const pathname = this.props.router.pathname
        if (MyApp.isRedirectToLogin(accessToken, pathname)) {
            this.props.router.push('/login')
        }
    }

    render() {
        const { Component, pageProps, store } = this.props
        return (
            <Container>
                <Provider store={store}>
                    {/* Wrap every page in Jss and Theme providers */}
                    <JssProvider
                        registry={this.pageContext.sheetsRegistry}
                        generateClassName={this.pageContext.generateClassName}
                    >
                        {/* MuiThemeProvider makes the theme available down the React
                        tree thanks to React context. */}
                        <MuiThemeProvider
                            theme={this.pageContext.theme}
                            sheetsManager={this.pageContext.sheetsManager}
                        >
                            {/* CssBaseline kickstart an elegant, consistent, and simple baseline to build upon. */}
                            <CssBaseline />
                            {/* Pass pageContext to the _document though the renderPage enhancer
                                to render collected styles on server side. */}
                            <Component pageContext={this.pageContext} {...pageProps} />
                        </MuiThemeProvider>
                    </JssProvider>
                </Provider>
            </Container>
        )
    }
}

export default withRedux(configureStore)(withReduxSaga(MyApp))