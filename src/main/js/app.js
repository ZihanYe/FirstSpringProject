'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
// end::vars[]

// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {employees: [], error: null};
	}

	componentDidMount() {
		fetch("/employees")
	      .then(res => res.json())
	      .then(
	        (result) => {
	          console.log(result);
	          this.setState({
	          	employees: result._embedded.employeeList
	          });
	        },
	        (error) => {
	          this.setState({
	            error
	          });
	        }
	      )
	}

	render() {
		return (
			<EmployeeList employees={this.state.employees}/>
		)
	}
}
// end::app[]

// tag::employee-list[]
class EmployeeList extends React.Component{
	render() {
		const employees = this.props.employees.map(employee =>
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>ID</th>
						<th>First Name</th>
						<th>Last Name</th>
					</tr>
					{employees}
				</tbody>
			</table>
		)
	}
}
// end::employee-list[]

// tag::employee[]
class Employee extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.employee.id}</td>
				<td>{this.props.employee.firstName}</td>
				<td>{this.props.employee.lastName}</td>
			</tr>
		)
	}
}
// end::employee[]

// tag::render[]
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
// end::render[]