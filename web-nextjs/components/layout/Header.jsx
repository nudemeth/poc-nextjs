import React from 'react'
import PropTypes from 'prop-types'
import jwt from 'jsonwebtoken'
import AppBar from '@material-ui/core/AppBar'
import ToolBar from '@material-ui/core/Toolbar'
import IconButton from '@material-ui/core/IconButton'
import Icon from '@material-ui/core/Icon'
import Button from '@material-ui/core/Button'
import Menu from '@material-ui/core/Menu'
import MenuItem from '@material-ui/core/MenuItem'
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
        this.state = {
            anchorEl: null
        }
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        handleDrawerToggle: PropTypes.func.isRequired,
        children: PropTypes.any.isRequired,
        accessToken: PropTypes.string
    }

    handleAccountMenuClick = (event) => {
        this.setState({ anchorEl: event.currentTarget })
    }

    handleAccountMenuClose = () => {
        this.setState({ anchorEl: null })
    }

    login = () => {
        const accessToken = this.props.accessToken
        if (accessToken) {
            const decoded = jwt.decode(accessToken, { complete: true })
            return (
                <React.Fragment>
                    <Button
                        color='inherit'
                        aria-owns={this.state.anchorEl ? 'account-menu' : null}
                        aria-haspopup="true"
                        onClick={this.handleAccountMenuClick}>
                        {decoded.payload.login}
                    </Button>
                    <Menu id='account-menu' anchorEl={this.state.anchorEl} open={Boolean(this.state.anchorEl)} onClose={this.handleAccountMenuClose}>
                        <MenuItem onClick={this.handleAccountMenuClose}>Profile</MenuItem>
                        <MenuItem><Link href='/logout'><span>Logout</span></Link></MenuItem>
                    </Menu>
                </React.Fragment>
            )
        } 
        return <Link prefetch href='/login'><Button color='inherit'>Login</Button></Link>
    }

    render() {
        const { classes } = this.props
        return (
            <AppBar className={classes.appBar}>
                <ToolBar>
                    <IconButton className={classes.menuButton} aria-label='Menu' onClick={this.props.handleDrawerToggle}>
                        <Icon>menu</Icon>
                    </IconButton>
                    <div className={classes.flex}>
                        {this.props.children}
                    </div>
                    {this.login()}
                </ToolBar>
            </AppBar>
        )
    }
}

const mapStateToProps = ({ identityReducer: { accessToken }}) => ({ accessToken })

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(Header))
export { Header }