import React from 'react';
import {Route, Link} from 'react-router-dom'
import Routes from '../route/Routes.jsx';
import { withStyles } from 'material-ui/styles';

const styles = theme => ({
    content: {
        backgroundColor: theme.palette.background.default,
        width: '100%',
        padding: theme.spacing.unit * 3,
        height: 'calc(100% - 56px)',
        marginTop: 56,
        [theme.breakpoints.up('sm')]: {
            height: 'calc(100% - 64px)',
            marginTop: 64,
        },
    },
});

class Content extends React.Component {
    constructor(props) {
        super(props);
    }

    renderComponent = () => {
        return Routes.map((route, index) => (
            <Route key={index} path={route.path} exact={route.exact} render={() => (<div>{route.component(this.props.model)}</div>)} />
        ));
    }

    render() {
        const { classes } = this.props;
        return (
            <main role="main" className={classes.content}>
                {this.renderComponent()}
            </main>
        );
    }
}

export default withStyles(styles,{ withTheme: true })(Content);