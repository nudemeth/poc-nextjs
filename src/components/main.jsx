import React from 'react'
import ReactDOM from 'react-dom'
import ReactDOMServer from 'react-dom/server'
import { StaticRouter } from 'react-router';
import { BrowserRouter } from 'react-router-dom';
import createBrowserHistory from 'history/createBrowserHistory';
import createMemoryHistory from 'history/createMemoryHistory';
import Container from './layout/Container.jsx';
import JssProvider from 'react-jss/lib/JssProvider';
import { MuiThemeProvider, createMuiTheme, createGenerateClassName } from 'material-ui/styles';
import { SheetsRegistry } from 'react-jss/lib/jss';

class UI {
    sheetsRegistry = new SheetsRegistry()

    renderServer = (location, model) => {
        const generateClassName = createGenerateClassName();
        return ReactDOMServer.renderToString(
            <StaticRouter location={location} context={model}>
                <JssProvider registry={this.sheetsRegistry} generateClassName={generateClassName}>
                    <Container model={model} />
                </JssProvider>
            </StaticRouter>
        );
    }

    renderClient = (model) => {
        let history = createBrowserHistory();
        let jssStyle = document.getElementById('jss-style');
        if (jssStyle && jssStyle.parentNode) {
            jssStyle.parentNode.removeChild(jssStyle);
        }
        return ReactDOM.hydrate(
            <BrowserRouter>
                <Container history={history} model={model} />
            </BrowserRouter>,
            document.getElementById('container')
        );
    }
}

export default UI;
exports.UI = UI;