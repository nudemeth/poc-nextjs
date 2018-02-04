import React from 'react';
import Header from './Header.jsx'
import SideBar from './SideBar.jsx'
import Content from './Content.jsx'
import { withStyles } from 'material-ui/styles';

const styles = theme => ({
    root: {
        width: '100%',
        height: '100%',
        zIndex: 1,
        overflow: 'hidden',
    },
    appFrame: {
        position: 'relative',
        display: 'flex',
        width: '100%',
        height: '100%',
    },
});

class Container extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { classes } = this.props;
        return (
            <div className={classes.root}>
                <div className={classes.appFrame}>
                    <Header />
                    <SideBar />
                    <Content {...this.props} />
                </div>
            </div>
        )
    }
}

export default withStyles(styles,{ withTheme: true })(Container);