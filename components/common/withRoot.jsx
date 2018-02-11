import React from 'react';
import PropTypes from 'prop-types';
import { MuiThemeProvider } from 'material-ui/styles';
import Reboot from 'material-ui/Reboot';
import getCssContext from './getCssContext';

function withRoot(Page) {
    class Root extends React.Component {
        constructor(props) {
            super(props);
        }

        cssContext = null;

        static getInitialProps = (ctx) => {
            if (Page.getInitialProps) {
                return Page.getInitialProps(ctx);
            }
        
            return {};
        };

        componentWillMount() {
            this.cssContext = this.props.cssContext || getCssContext();
        }

        componentDidMount() {
            // Remove the server-side injected CSS.
            const jssStyles = document.querySelector('#jss-server-side');
            if (jssStyles && jssStyles.parentNode) {
                jssStyles.parentNode.removeChild(jssStyles);
            }
        }

        render() {
            // MuiThemeProvider makes the theme available down the React tree thanks to React context.
            return (
                <MuiThemeProvider
                    theme={this.cssContext.theme}
                    sheetsManager={this.cssContext.sheetsManager}
                >
                    {/* Reboot kickstart an elegant, consistent, and simple baseline to build upon. */}
                    <Reboot />
                    <Page {...this.props} />
                </MuiThemeProvider>
            );
        }
    }

    Root.propTypes = {
        cssContext: PropTypes.object,
    };

    return Root;
}

export default withRoot;