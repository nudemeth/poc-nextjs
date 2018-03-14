import React from 'react';
import PropTypes from 'prop-types';
import AppBar from 'material-ui/AppBar';
import ToolBar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import IconButton from 'material-ui/IconButton';
import Icon from 'material-ui/Icon';
import Button from 'material-ui/Button';
import Link from 'next/link';
import { withStyles } from 'material-ui/styles';

const drawerWidth = 240;
const styles = theme => ({
    flex: {
        flex: 1,
    },
    menuButton: {
        marginLeft: -12,
        marginRight: 10,
        [theme.breakpoints.up('md')]: {
            display: 'none',
        }
    },
    appBar: {
        position: 'absolute',
        marginLeft: drawerWidth,
        [theme.breakpoints.up('md')]: {
            width: `calc(100% - ${drawerWidth}px)`,
        },
    },
});

class Header extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired
    }

    render() {
        const { classes, theme } = this.props;
        return (
            <AppBar className={classes.appBar}>
                <ToolBar>
                    <IconButton className={classes.menuButton} aria-label="Menu" onClick={this.props.handleDrawerToggle}>
                        <Icon>menu</Icon>
                    </IconButton>
                    <div className={classes.flex}>
                        {this.props.children}
                    </div>
                    <Link prefetch href="/login">
                        <Button color="inherit">Login</Button>
                    </Link>
                </ToolBar>
            </AppBar>
        );
    }
}

export default withStyles(styles, { withTheme: true })(Header);