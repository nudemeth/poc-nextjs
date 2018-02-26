import React from 'react';
import PropTypes from 'prop-types';
import Link from 'next/link';
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

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired
    }

    render() {
        const { classes } = this.props;
        return [
            <div key={uuidv4()} className={classes.drawerHeader} />,
            <Divider key={uuidv4()} />,
            <List key={uuidv4()}>
                <Link prefetch href="/">
                    <ListItem button>
                        <Icon color="primary">home</Icon>
                        <ListItemText primary="Home" />
                    </ListItem>
                </Link>
                <Link prefetch href="/products">
                    <ListItem button>
                        <Icon color="primary">shop</Icon>
                        <ListItemText primary="Products" />
                    </ListItem>
                </Link>
            </List>
        ];
    }
}

export default withStyles(styles, { withTheme: true })(DrawerMenu);