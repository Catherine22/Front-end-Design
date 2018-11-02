import React, {Component} from 'react';
import './CommentInput.css';


class CommentInput extends Component {

    static defaultProps = {
        wordings: {
            userName: '用户名：',
            comment: '评论内容：',
            submit: '提交'
        }
    };

    constructor(props) {
        super(props);

        this.state = {
            username: '',
            content: ''
        }
    }

    componentDidMount() {
        this.commentInput.focus();
    }

    _onUserLabelChange(event) {
        this.setState({
            username: event.target.value
        })
    }

    _onCommentLabelChange(event) {
        this.setState({
            content: event.target.value
        })
    }

    _onSubmit() {
        if (this.props.onSubmit) {
            this.props.onSubmit(this.state);
        }
        this.setState({
            content: ''
        });
    }


    render() {
        return (
            <div>
                <div className="commentInput">
                    <div className="inputTitle">
                        <label className="text">{this.props.wordings.userName}</label>
                        <label className="text">{this.props.wordings.comment}</label>
                    </div>
                    <div className="inputField">
                        <div>
                            <label>
                                <input type="standardText" className="singleLineInputField"
                                       value={this.state.username}
                                       onChange={this._onUserLabelChange.bind(this)}/>
                            </label>
                        </div>
                        <div>
                            <label>
                                <input ref={(input) => {
                                           this.commentInput = input;
                                       }}
                                       type="standardText" className="multiLinesInputField"
                                       value={this.state.content}
                                       onChange={this._onCommentLabelChange.bind(this)}/>
                            </label>
                        </div>
                        <button className="standardButton"
                                onClick={this._onSubmit.bind(this)}>{this.props.wordings.submit}</button>
                    </div>
                </div>
            </div>
        );
    }
}

export {CommentInput};
