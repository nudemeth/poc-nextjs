import React from 'react';
import Card, { CardActions, CardContent } from 'material-ui/Card';
import Button from 'material-ui/Button';
import Typography from 'material-ui/Typography';
import TextField from 'material-ui/TextField';
import Divider from 'material-ui/Divider';
import { withStyles } from 'material-ui/styles';

const styles = {
    card: {
        maxWidth: 345,
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
};

class Login extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { classes } = this.props;
        return (
            <Card className={classes.card}>
                <CardContent>
                    <Typography variant="headline" component="h2">
                        Sign in to your account
                    </Typography>
                    <TextField
                        id="username"
                        label="Username"
                        margin="normal"
                        fullWidth
                    />
                    <TextField
                        id="password"
                        label="Password"
                        type="password"
                        margin="normal"
                        fullWidth
                    />
                </CardContent>
                <CardActions className={classes.cardActions}>
                    <Button variant="raised" color="primary" className={classes.signInButton}>
                        Sign In
                    </Button>
                </CardActions>
                <div className={classes.dividerContainer}>
                    <Divider className={classes.divider} />
                    <span className={classes.dividerText}>OR</span>
                    <Divider className={classes.divider}/>
                </div>
                <CardActions className={classes.cardActions}>
                    <Button variant="raised" color="secondary" className={classes.signInButton}>
                        Sign In With Github
                    </Button>
                </CardActions>
            </Card>
        );
    }
}

export default withStyles(styles)(Login);