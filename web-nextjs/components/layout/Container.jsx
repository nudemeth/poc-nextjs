import React from 'react'
import PropTypes from 'prop-types'
import Head from 'next/head'
import Header from './Header'
import SideBar from './SideBar'
import { withStyles } from '@material-ui/core/styles'

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
    content: {
        backgroundColor: theme.palette.background.default,
        width: '100%',
        padding: theme.spacing.unit * 3,
        height: 'calc(100% - 56px)',
        overflowY: 'auto',
        marginTop: 56,
        [theme.breakpoints.up('sm')]: {
            height: 'calc(100% - 64px)',
            marginTop: 64,
        },
    },
})

class Container extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            menuOpen: false
        }
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        title: PropTypes.string.isRequired,
        header: PropTypes.any.isRequired,
        children: PropTypes.any.isRequired
    }

    handleDrawerToggle = () => {
        this.setState({ menuOpen: !this.state.menuOpen })
    };

    render() {
        const { classes } = this.props
        return (
            <div className={classes.root}>
                <div className={classes.appFrame}>
                    <Head>
                        <title>{this.props.title}</title>
                    </Head>
                    <Header handleDrawerToggle={this.handleDrawerToggle}>
                        {this.props.header}
                    </Header>
                    <SideBar menuOpen={this.state.menuOpen} handleDrawerToggle={this.handleDrawerToggle} />
                    <main role='main' className={classes.content}>
                        {this.props.children}
                    </main>
                </div>
            </div>
        )
    }
}

export default withStyles(styles, { withTheme: true })(Container)
export { Container }