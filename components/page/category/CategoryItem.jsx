import React from 'react';
import PropTypes from 'prop-types';
import GridList, { GridListTile, GridListTileBar } from 'material-ui/GridList';
import Icon from 'material-ui/Icon';
import IconButton from 'material-ui/IconButton';
import Typography from 'material-ui/Typography';
import { updateSelectedCategory } from '../../../actions/actions';
import { withStyles } from 'material-ui/styles';
import { connect } from 'react-redux';

const defaultColor = theme => theme.palette.grey[500];
const selectedColor = theme => theme.palette.secondary.main;
const categoryStyle = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: '1px',
    borderStyle: 'solid',
    backgroundColor: '#fff'
}

const styles = theme => ({
    iconButtonRoot: {
        width: '100%',
        height: '100%'
    },
    iconButtonLabel: {
        width: '100%',
        height: 80,
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'space-evenly'
    },
    icon: {
        color: defaultColor(theme)
    },
    selectedIcon: {
        color: selectedColor(theme)
    },
    categoryTile: {
        ...categoryStyle,
        borderColor: defaultColor(theme)
    },
    selectedCategoryTile: {
        ...categoryStyle,
        borderColor: selectedColor(theme)
    },
    categoryRoot: {
        height: 184,
        padding: 2,
        [theme.breakpoints.up('lg')]: {
            width: '33.3333%'
        },
        [theme.breakpoints.down('md')]: {
            width: '50%'
        },
        [theme.breakpoints.down('xs')]: {
            width: '100%'
        }
    }
});

class CategoryItem extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isSelected: this.props.category.isSelected
        }
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        category: PropTypes.object.isRequired
    }

    handleCategoryToggle = () => {
        this.props.dispatch(updateSelectedCategory(this.props.category, !this.state.isSelected));
        this.setState({ isSelected: !this.state.isSelected });
    }

    render() {
        const { classes, category } = this.props;
        const iconClass = this.state.isSelected ? classes.selectedIcon : classes.icon;
        const categoryTileClass = this.state.isSelected ? classes.selectedCategoryTile : classes.categoryTile;
        return (
            <GridListTile classes={{root: classes.categoryRoot, tile: categoryTileClass}}>
                <IconButton disableRipple classes={{root: classes.iconButtonRoot, label: classes.iconButtonLabel}} onClick={this.handleCategoryToggle}>
                    <Icon className={iconClass} >{category.image}</Icon>
                    <Typography component="span">{category.name}</Typography>
                </IconButton>
            </GridListTile>
        );
    }
}

export default connect()(withStyles(styles, { withTheme: true })(CategoryItem));
export { CategoryItem };