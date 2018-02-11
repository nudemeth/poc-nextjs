import React from 'react';
import PropTypes from 'prop-types';
import Divider from 'material-ui/Divider';
import List, { ListItem, ListItemIcon, ListItemText } from 'material-ui/List';
import Icon from 'material-ui/Icon';
import { withStyles } from 'material-ui/styles';
import uuidv4 from 'uuid/v4';

const styles = theme => ({
    drawerHeader: theme.mixins.toolbar,
});

class DrawerMenu extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { classes } = this.props;
        return [
            <div key={uuidv4()} className={classes.drawerHeader} />,
            <Divider key={uuidv4()} />,
            <List key={uuidv4()}>
                <ListItem button>
                    <Icon color="inherit">home</Icon>
                    <ListItemText primary="Home" />
                </ListItem>
                <ListItem button>
                    <Icon color="inherit">info</Icon>
                    <ListItemText primary="Info" />
                </ListItem>
            </List>
        ];
    }
}

DrawerMenu.propTypes = {
    classes: PropTypes.object.isRequired,
    theme: PropTypes.object.isRequired,
};

export default withStyles(styles, { withTheme: true })(DrawerMenu);