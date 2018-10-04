import React from 'react'
import PropTypes from 'prop-types'
import { withStyles } from '@material-ui/core/styles'
import Drawer from '@material-ui/core/Drawer'
import Hidden from '@material-ui/core/Hidden'
import DrawerMenu from './DrawerMenu'
import uuidv4 from 'uuid/v4'

const drawerWidth = 240
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
})

class SideBar extends React.Component {
    constructor(props) {
        super(props)
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        handleDrawerToggle: PropTypes.func.isRequired,
        menuOpen: PropTypes.bool.isRequired
    }

    render() {
        const { classes } = this.props
        return (
            <React.Fragment>
                <Hidden key={uuidv4()} mdUp>
                    <Drawer
                        variant='temporary'
                        open={this.props.menuOpen}
                        classes={{paper: classes.drawerPaper, docked: classes.drawerDocked, }}
                        onClose={this.props.handleDrawerToggle}
                        ModalProps={{
                            keepMounted: true, // Better open performance on mobile.
                        }}
                    >
                        <DrawerMenu/>
                    </Drawer>
                </Hidden>
                <Hidden key={uuidv4()} smDown implementation='css'>
                    <Drawer
                        variant='permanent'
                        open
                        classes={{paper: classes.drawerPaper, docked: classes.drawerDocked, }}
                    >
                        <DrawerMenu/>
                    </Drawer>
                </Hidden>
            </React.Fragment>
        )
    }
}

export default  withStyles(styles, { withTheme: true })(SideBar)
export { SideBar }