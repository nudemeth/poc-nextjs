import React from 'react';

class TitleBar extends React.Component {
    constructor(props) {
        super(props);
    }

    isBrowser = () => {
        return typeof window === 'object' && !!window.document;
    }

    componentWillMount() {
        if (this.isBrowser()) {
            document.title = this.props.title;
        }
    }

    componentWillUpdate(nextProps, nextState) {
        if (this.isBrowser()) {
            document.title = nextProps.title;
        }
    }

    render() {
        return null;
    }
}

export default TitleBar;