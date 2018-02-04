import React from 'react';
import PropTypes from 'prop-types';
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
        return (
            <div className={this.props.classes.root}>
                <div className={this.props.classes.appFrame}>
                    <Header />
                    <SideBar />
                    <Content model={this.props.model} />
                </div>
            </div>
        );
    }
}

Container.propTypes = {
    classes: PropTypes.object.isRequired
};

export default withStyles(styles, { withTheme: true })(Container);