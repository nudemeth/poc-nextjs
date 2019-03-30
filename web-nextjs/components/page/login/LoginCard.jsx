/* eslint-disable no-console */
import React from 'react'
import PropTypes from 'prop-types'
import Card from '@material-ui/core/Card'
import CardActions from '@material-ui/core/CardActions'
import CardContent from '@material-ui/core/CardContent'
import Button from '@material-ui/core/Button'
import Typography from '@material-ui/core/Typography'
import TextField from '@material-ui/core/TextField'
import Divider from '@material-ui/core/Divider'
import { withStyles } from '@material-ui/core/styles'
import { connect } from 'react-redux'

const styles = () => ({
    card: {
        maxWidth: 345,
        height: 320,
        margin: 'auto'
    },
    cardActions: {
        padding: '0 16px'
    },
    signInButton: {
        width: '100%',
        margin: 0
    },
    dividerContainer: {
        padding: '0 16px',
        width: '100%',
        display: 'flex',
        alignItems: 'center'
    },
    divider: {
        width: '40%'
    },
    dividerText: {
        width: '20%',
        textAlign: 'center'
    }
})

class LoginCard extends React.Component {
    constructor(props) {
        super(props)
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        user: PropTypes.string,
        sites: PropTypes.arrayOf(PropTypes.shape({
            name: PropTypes.string,
            url: PropTypes.string
        })).isRequired
    }

    handleGithubSignIn = () => {
        if (window) {
            const site = this.props.sites.find((s) => s.name === 'github')
            window.open(site.url)
        }
    }

    render() {
        const { classes } = this.props
        return (
            <Card className={classes.card}>
                <CardContent>
                    <Typography variant='headline' component='h2'>
                        Sign in to your account
                    </Typography>
                    <TextField
                        id='username'
                        label='Username'
                        margin='normal'
                        fullWidth
                    />
                    <TextField
                        id='password'
                        label='Password'
                        type='password'
                        margin='normal'
                        fullWidth
                    />
                </CardContent>
                <CardActions className={classes.cardActions}>
                    <Button variant='raised' color='primary' className={classes.signInButton}>
                        Sign In
                    </Button>
                </CardActions>
                <div className={classes.dividerContainer}>
                    <Divider className={classes.divider}/>
                    <span className={classes.dividerText}>OR</span>
                    <Divider className={classes.divider}/>
                </div>
                <CardActions className={classes.cardActions}>
                    <Button variant='raised' color='secondary' className={classes.signInButton} onClick={this.handleGithubSignIn}>
                        Sign In With Github
                    </Button>
                </CardActions>
            </Card>
        )
    }
}

const mapStateToProps = ({ identityReducer: { sites }}) => ({ sites })

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(LoginCard))
export { LoginCard }