import React from 'react';
import PropTypes from 'prop-types';
import Input, { InputAdornment  } from 'material-ui/Input';
import MenuItem from 'material-ui/Menu/MenuItem';
import Icon from 'material-ui/Icon';
import { withStyles } from 'material-ui/styles';

const styles = theme => ({
    wrapper: {
        display: 'flex',
        justifyContent: 'flex-end'
    },
    textField: {
        border: '1px solid white',
        color: 'white',
        paddingLeft: '5px'
    }
});

class HeaderBar extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired
    }

    render() {
        const { classes } = this.props;
        return (
            <div className={classes.wrapper}>
                <Input
                    id="search"
                    type="search"
                    className={classes.textField}
                    startAdornment={
                        <InputAdornment position="start">
                            <Icon>search</Icon>
                        </InputAdornment>
                    }
                />
            </div>
        );
    }
}

export default withStyles(styles, { withTheme: true })(HeaderBar);