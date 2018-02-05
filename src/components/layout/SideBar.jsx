import React from 'react';
import {Link} from 'react-router-dom';
import {Router} from 'react-router';
import { withStyles } from 'material-ui/styles';
import Drawer from 'material-ui/Drawer';
import Divider from 'material-ui/Divider';
import Hidden from 'material-ui/Hidden';
import List, { ListItem, ListItemIcon } from 'material-ui/List';
import Icon from 'material-ui/Icon';
import { withRouter } from 'react-router-dom';
import uuidv4 from 'uuid/v4';

const drawerWidth = 240;
const styles = theme => ({
    drawerHeader: theme.mixins.toolbar,
    drawerDocked: {
        height: '100%',
    },
    drawerPaper: {
        width: 250,
        height: '100%',
        [theme.breakpoints.up('md')]: {
            width: drawerWidth,
            position: 'relative',
        },
    },
});

class SideBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            menuOpen: this.props.menuOpen,
        }
    }

    handleHomeClick = () => {
        this.props.history.push('/')
    };

    handleAboutClick = () => {
        this.props.history.push('/about')
    };

    render() {
        const { classes, theme } = this.props;
        const drawer = [
            <div key={uuidv4()} className={classes.drawerHeader} />,
            <Divider key={uuidv4()} />,
            <List key={uuidv4()}>
                <ListItem button onClick={this.handleHomeClick}>
                    <Icon color="inherit">home</Icon>
                </ListItem>
                <ListItem button onClick={this.handleAboutClick}>
                    <Icon color="inherit">info</Icon>
                </ListItem>
            </List>
        ];

        return [
            <Hidden key={uuidv4()} mdUp>
                <Drawer
                    type="temporary"
                    open={this.props.menuOpen}
                    classes={{paper: classes.drawerPaper, docked: classes.drawerDocked, }}
                    onClose={this.props.handleDrawerToggle}
                    ModalProps={{
                        keepMounted: true, // Better open performance on mobile.
                    }}
                >
                    {drawer}
                </Drawer>
            </Hidden>,
            <Hidden key={uuidv4()} smDown implementation="css">
                <Drawer
                    type="permanent"
                    open
                    classes={{paper: classes.drawerPaper, docked: classes.drawerDocked, }}
                >
                    {drawer}
                </Drawer>
            </Hidden>
        ];
    }
}

export default withRouter(withStyles(styles, { withTheme: true })(SideBar));