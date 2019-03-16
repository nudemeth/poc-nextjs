import React from 'react'
import PropTypes from 'prop-types'
import AppBar from '@material-ui/core/AppBar'
import ToolBar from '@material-ui/core/Toolbar'
import IconButton from '@material-ui/core/IconButton'
import Icon from '@material-ui/core/Icon'
import Button from '@material-ui/core/Button'
import Link from 'next/link'
import { withStyles } from '@material-ui/core/styles'
import { connect } from 'react-redux'

const drawerWidth = 240
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
})

class Header extends React.Component {
    constructor(props) {
        super(props)
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        handleDrawerToggle: PropTypes.func.isRequired,
        children: PropTypes.any.isRequired,
        user: PropTypes.string
    }

    render() {
        const { classes } = this.props
        const user = this.props.user
        const loginOrUser = user ? <Button color='inherit'>{user}</Button> : <Link prefetch href='/login'><Button color='inherit'>Login</Button></Link>
        return (
            <AppBar className={classes.appBar}>
                <ToolBar>
                    <IconButton className={classes.menuButton} aria-label='Menu' onClick={this.props.handleDrawerToggle}>
                        <Icon>menu</Icon>
                    </IconButton>
                    <div className={classes.flex}>
                        {this.props.children}
                    </div>
                    {loginOrUser}
                </ToolBar>
            </AppBar>
        )
    }
}

const mapStateToProps = ({ identityReducer: { user }}) => ({ user })

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(Header))
export { Header }