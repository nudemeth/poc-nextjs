import React from 'react';
import Link from 'next/link';

class SideBar extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <span>This is Sidebar</span>
                <Link href="/">
                    <a>Home</a>
                </Link>
                <Link href="/about">
                    <a>About</a>
                </Link>
            </div>
        );
    }
}

export default SideBar;