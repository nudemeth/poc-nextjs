import React from 'react';
import PropTypes from 'prop-types';
import GridList, { GridListTile, GridListTileBar } from 'material-ui/GridList';
import Icon from 'material-ui/Icon';
import IconButton from 'material-ui/IconButton';
import Hidden from 'material-ui/Hidden';
import Typography from 'material-ui/Typography';
import uuidv4 from 'uuid/v4';
import { withStyles } from 'material-ui/styles';
import { connect } from 'react-redux';
import { loadProducts } from '../../../actions';

const styles = theme => ({
    gridList: {
        width: '100%',
        height: '100%',
        [theme.breakpoints.up('lg')]: {
            paddingLeft: '15%',
            paddingRight: '15%'
        }
    },
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
        color: theme.palette.secondary.main
    },
    category: {
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        borderWidth: '1px',
        borderStyle: 'solid',
        borderColor: theme.palette.secondary.main
    }
});

const createCategoryItem = (category, classes) => {
    return (
        <GridListTile key={category.id} classes={{tile: classes.category}}>
            <IconButton disableRipple classes={{root: classes.iconButtonRoot, label: classes.iconButtonLabel}}>
                <Icon className={classes.icon}>{category.image}</Icon>
                <Typography component="span">{category.name}</Typography>
            </IconButton>
        </GridListTile>
    );
}

class CategoryList extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired
    }

    render() {
        const { classes, categories } = this.props;
        const items = categories.map((category, index) => createCategoryItem(category, classes));
        return [
            <Hidden mdDown key={uuidv4()}>
                <GridList cellHeight={180} cols={3} className={classes.gridList}>
                    {items}
                </GridList>
            </Hidden>
            ,<Hidden xsDown lgUp key={uuidv4()}>
                <GridList cellHeight={180} cols={2} className={classes.gridList}>
                    {items}
                </GridList>
            </Hidden>
            ,<Hidden smUp key={uuidv4()}>
                <GridList cellHeight={180} cols={1} className={classes.gridList}>
                    {items}
                </GridList>
            </Hidden>
        ];
    }
}

const mapStateToProps = ({ categories, error }) => ({ categories, error });

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(CategoryList));