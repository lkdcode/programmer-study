import React from "react";
import Notification from "./Notification";

const reserveNotifications = [
  {
    message: "메시지 11111111111",
  },
  {
    message: "메시지 22222222222",
  },
  {
    message: "메시지 33333333333",
  },
  {
    message: "메시지 11111111111",
  },
  {
    message: "메시지 22222222222",
  },
  {
    message: "메시지 33333333333",
  },
]

var timer;

class NotificationList extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      notifications: [],
    };
  }
  
  componentDidMount() {
    const { notifications } = this.state;
    timer = setInterval(() => {
      if (notifications.length < reserveNotifications.length) {
        const index = notifications.length;
        notifications.push(reserveNotifications[index]);
        this.setState({
          notifications: notifications,
        });
      } else {
        clearInterval(timer);
      }
    }, 1000);
  }
  
  componentWillUnmount() {
    if (timer) {
      clearInterval(timer);
    }
  }

  render() {
    return (
      <div>
        {this.state.notifications.map(e => {
          return <Notification message={e.message} />;
        })}
      </div>
    )
  }
}

export default NotificationList