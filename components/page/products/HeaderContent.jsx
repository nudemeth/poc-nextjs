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
        color: '#fff',
        backgroundColor: 'rgba(255,255,255,0.2)'
    },
    underline: {
        '&:before': {
            backgroundColor: 'rgba(255,255,255,0.42)'
        },
        '&:hover': {
            '&:not(.MuiInput-disabled)': {
                '&:before': {
                    backgroundColor: 'rgba(255,255,255,0.6)'
                }
            }
        },
        '&:after': {
            backgroundColor: 'rgba(255,255,255,1)'
        }
    },
    adorment: {
        marginLeft: 8
    }
});

class HeaderContent extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired
    }

    render() {
        return (
            <div className={this.props.classes.wrapper}>
                <Input
                    id="search"
                    type="search"
                    classes={{
                        root: this.props.classes.textField,
                        underline: this.props.classes.underline
                    }}
                    startAdornment={
                        <InputAdornment position="start" className={this.props.classes.adorment}>
                            <Icon>search</Icon>
                        </InputAdornment>
                    }
                />
            </div>
        );
    }
}

export default withStyles(styles, { withTheme: true })(HeaderContent);