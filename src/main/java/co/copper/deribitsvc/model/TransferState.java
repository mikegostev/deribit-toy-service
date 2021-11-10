package co.copper.deribitsvc.model;

public enum TransferState {
	pending,
	completed,
	rejected,
	replaced,
	unconfirmed,
	confirmed,
	cancelled,
	interrupted,
	prepared,
	waiting_for_admin;
}
