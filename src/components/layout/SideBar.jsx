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

const drawerWidth = 240;
const styles = theme => ({
    drawerHeader: theme.mixins.toolbar,
    drawerPaper: {
        width: 250,
        [theme.breakpoints.up('md')]: {
            width: drawerWidth,
            position: 'relative',
            height: '100%',
        },
    },
});

class SideBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            mobileOpen: false,
        }
    }

    handleDrawerToggle = () => {
        this.setState({ mobileOpen: !this.state.mobileOpen });
    };

    handleHomeClick = () => {
        this.props.history.push('/')
    };

    handleAboutClick = () => {
        this.props.history.push('/about')
    };

    render() {
        const { classes, theme } = this.props;
        const drawer = (
            <div>
                <div className={classes.drawerHeader} />
                <Divider />
                <List>
                    <ListItem button onClick={this.handleHomeClick}>
                        <Icon color="inherit">home</Icon>
                    </ListItem>
                    <ListItem button onClick={this.handleAboutClick}>
                        <Icon color="inherit">info</Icon>
                    </ListItem>
                </List>
            </div>
        );

        return (
            <div>
                <Hidden mdUp>
                    <Drawer
                        type="temporary"
                        open={this.state.mobileOpen}
                        classes={{paper: classes.drawerPaper,}}
                        onClose={this.handleDrawerToggle}
                        ModalProps={{
                            keepMounted: true, // Better open performance on mobile.
                        }}
                    >
                    {drawer}
                    </Drawer>
                </Hidden>
                <Hidden smDown implementation="css">
                    <Drawer
                        type="permanent"
                        open
                        classes={{
                            paper: classes.drawerPaper,
                        }}
                    >
                    {drawer}
                    </Drawer>
                </Hidden>
            </div>
        )
    }
}

export default withRouter(withStyles(styles, { withTheme: true })(SideBar));