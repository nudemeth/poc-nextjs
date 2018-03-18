import React from 'react';
import PropTypes from 'prop-types';
import Input, { InputAdornment  } from 'material-ui/Input';
import TextField from 'material-ui/TextField';
import MenuItem from 'material-ui/Menu/MenuItem';
import Icon from 'material-ui/Icon';
import { withStyles } from 'material-ui/styles';

const textFieldWidth = 250;
const styles = theme => ({
    wrapper: {
        width: '100%',
        marginBottom: 50
    },
    textField: {
        width: textFieldWidth,
        marginLeft: 'calc(50% - ' + (textFieldWidth / 2) + 'px)',
        marginRight: 'calc(50% - ' + (textFieldWidth / 2) + 'px)',
        [theme.breakpoints.up('md')]: {
            width: 500,
            marginLeft: 'calc(50% - ' + textFieldWidth + 'px)',
            marginRight: 'calc(50% - ' + textFieldWidth + 'px)'
        },
    }
});

class Criteria extends React.Component {
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
                <TextField
                    id="category"
                    label="Category"
                    select
                    className={classes.textField}
                    value={0}
                    margin="normal"
                >
                    <MenuItem key={0} value={0}>All</MenuItem>
                    <MenuItem key={1} value={1}>Accessory</MenuItem>
                    <MenuItem key={2} value={2}>Cloth</MenuItem>
                </TextField>
                <TextField
                    id="filter"
                    type="search"
                    label="Filter"
                    className={classes.textField}
                    margin="normal"
                    InputProps={{startAdornment:<InputAdornment position="start"><Icon>search</Icon></InputAdornment>}}
                />
            </div>
        );
    }
}

export default withStyles(styles, { withTheme: true })(Criteria);
export { Criteria };